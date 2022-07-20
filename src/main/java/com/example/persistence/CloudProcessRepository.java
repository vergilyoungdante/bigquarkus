package com.example.persistence;

import com.example.common.page.MyPage;
import com.example.domain.CloudComputer;
import com.example.domain.CloudProcess;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.persistence
 * @Author: vergil young
 * @CreateTime: 2022-07-19  19:19
 * @Description: TODO
 */
@ApplicationScoped
public class CloudProcessRepository implements PanacheRepository<CloudProcess> {

    public MyPage<CloudProcess> findPage(Map<String, Object> parameters, Page page){
        MyPage<CloudProcess> result = new MyPage<>(page.index,page.size,0,null);
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
}
