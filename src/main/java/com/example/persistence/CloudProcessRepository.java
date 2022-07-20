package com.example.persistence;

import com.example.domain.CloudProcess;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.persistence
 * @Author: vergil young
 * @CreateTime: 2022-07-19  19:19
 * @Description: TODO
 */
@ApplicationScoped
public class CloudProcessRepository implements PanacheRepository<CloudProcess> {
}
