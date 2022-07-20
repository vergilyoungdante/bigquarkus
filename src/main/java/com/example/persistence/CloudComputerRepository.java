package com.example.persistence;

import com.example.common.page.MyPage;
import com.example.domain.CloudComputer;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;


import javax.enterprise.context.ApplicationScoped;
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

}
