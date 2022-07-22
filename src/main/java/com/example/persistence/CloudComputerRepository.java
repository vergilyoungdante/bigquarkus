package com.example.persistence;

import com.example.common.page.MyPage;
import com.example.domain.CloudComputer;
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
 * @CreateTime: 2022-07-19  19:18
 * @Description: TODO
 */
@ApplicationScoped//这个一定要扫一下，否则找不到它，跟PanacheEntity那个还不一样
public class CloudComputerRepository implements PanacheRepository<CloudComputer> {

    public CloudComputer findByName(String name){
        return find("name", name).firstResult();
    }

    public void deleteByName(String name){
        delete("name", name);
    }

    public MyPage<CloudComputer> findPage(Map<String, Object> parameters, Page page){
        MyPage<CloudComputer> result = new MyPage<>(page.index,page.size,0,null);
        if ( parameters == null ) {
            int total = listAll().size();
            result.setTotal(total);
            result.setList(findAll().page(page).list());
            return result;
        }

        Map<String, Object> nonNullParams = parameters.entrySet().stream()
                .filter( entry -> entry.getValue() != null )
                .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ) );

        if ( nonNullParams.isEmpty() ) {
            int total = listAll().size();
            result.setTotal(total);
            result.setList(findAll().page(page).list());
            return result;
        }

        String query = nonNullParams.entrySet().stream()
                .map( entry -> entry.getKey() + "=:" + entry.getKey() )
                .collect( Collectors.joining(" and ") );

        result.setTotal((int)find(query, nonNullParams).count());
        result.setList(find(query, nonNullParams).page(page).list());
        return result;
    }

    public MyPage<CloudComputer> JpaSpecificationPage(CloudComputer cloudComputer, Page page){

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CloudComputer> query = criteriaBuilder.createQuery(CloudComputer.class);
        Root<CloudComputer> root = query.from(CloudComputer.class);
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtil.isNotEmpty(cloudComputer.getName())){
            predicates.add(criteriaBuilder.like(root.get("name"),"%"+cloudComputer.getName()+"%"));
        }
        if(cloudComputer.getId()!=null){
            predicates.add(criteriaBuilder.equal(root.get("id"),cloudComputer.getId()));
        }
        if(cloudComputer.getCpuPower()>0){
            predicates.add(criteriaBuilder.greaterThan(root.get("cpupower"),cloudComputer.getCpuPower()));
        }
        if(cloudComputer.getMemory()>0){
            predicates.add(criteriaBuilder.greaterThan(root.get("memory"),cloudComputer.getMemory()));
        }
        if(cloudComputer.getNetworkBandwidth()>0){
            predicates.add(criteriaBuilder.greaterThan(root.get("networkbandwidth"),cloudComputer.getNetworkBandwidth()));
        }
        query.where(predicates.toArray(Predicate[]::new));
        List<CloudComputer> list = getEntityManager().createQuery(query).getResultList();
        int count = list.size();
        list = list.stream().skip((page.index-1)* page.size).limit(page.size).collect(Collectors.toList());
        return new MyPage<>(page.index,page.size,count,list);
    }
}
