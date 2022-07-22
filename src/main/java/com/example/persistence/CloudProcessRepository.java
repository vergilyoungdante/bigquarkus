package com.example.persistence;

import com.example.common.page.MyPage;
import com.example.domain.CloudProcess;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.smallrye.openapi.runtime.util.StringUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.persistence
 * @Author: vergil young
 * @CreateTime: 2022-07-19  19:19
 * @Description: jap api
 */
@ApplicationScoped
public class CloudProcessRepository implements PanacheRepository<CloudProcess> {

    //拼sql，不建议使用
    public MyPage<CloudProcess> findPage(Map<String, Object> parameters, Page page) {
        MyPage<CloudProcess> result = new MyPage<>(page.index, page.size, 0, null);
        if (parameters == null) {
            int total = listAll().size();
            result.setTotal(total);
            result.setList(findAll().page(page).list());
            return result;
        }

        Map<String, Object> nonNullParams = parameters.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (nonNullParams.isEmpty()) {
            int total = listAll().size();
            result.setTotal(total);
            result.setList(findAll().page(page).list());
            return result;
        }

        String query = nonNullParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(" and "));

        result.setTotal((int) find(query, nonNullParams).count());
        result.setList(find(query, nonNullParams).page(page).list());
        return result;
    }

    //jpa specification查询，推荐使用
    public MyPage<CloudProcess> jpaSpecificationPage(CloudProcess cloudProcess, Page page) {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CloudProcess> query = criteriaBuilder.createQuery(CloudProcess.class);
        Root<CloudProcess> root = query.from(CloudProcess.class);
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtil.isNotEmpty(cloudProcess.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + cloudProcess.getName() + "%"));
        }
        if (cloudProcess.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), cloudProcess.getId()));
        }
        if (cloudProcess.getRequiredCpuPower() > 0) {
            predicates.add(criteriaBuilder.greaterThan(root.get("requirecpupower"), cloudProcess.getRequiredCpuPower()));
        }
        if (cloudProcess.getRequiredMemory() > 0) {
            predicates.add(criteriaBuilder.greaterThan(root.get("requirememory"), cloudProcess.getRequiredMemory()));
        }
        if (cloudProcess.getRequiredNetworkBandwidth() > 0) {
            predicates.add(criteriaBuilder.greaterThan(root.get("networkbandwidth"), cloudProcess.getRequiredNetworkBandwidth()));
        }
        query.where(predicates.toArray(Predicate[]::new));
        List<CloudProcess> list = getEntityManager().createQuery(query)
                .setFirstResult(page.size * (page.index - 1))
                .setMaxResults(page.size)
                .getResultList();

        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(CloudProcess.class)));
        cq.where(predicates.toArray(Predicate[]::new));
        Long count = getEntityManager().createQuery(cq).getSingleResult();

        return new MyPage<>(page.index, page.size, count.intValue(), list);
    }
}
