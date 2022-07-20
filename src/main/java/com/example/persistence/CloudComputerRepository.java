package com.example.persistence;

import com.example.domain.CloudComputer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;


import javax.enterprise.context.ApplicationScoped;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.persistence
 * @Author: vergil young
 * @CreateTime: 2022-07-19  19:18
 * @Description: TODO
 */
@ApplicationScoped//这个一定要扫一下，否则找不到它，跟PanacheEntity那个还不一样
public class CloudComputerRepository implements PanacheRepository<CloudComputer> {
}
