package org.hmily.spring.ioc.overview.domain;

import org.hmily.spring.ioc.overview.enums.City;
import org.springframework.core.io.Resource;

import javax.naming.ldap.PagedResultsControl;
import java.util.Arrays;
import java.util.List;

public class User {

    private Long  id;

    private String name;

    private City city;

    private Resource configFileLocation;

    private City[] workcities;

    private List<City> lifeCities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Resource getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(Resource configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

    public City[] getWorkcities() {
        return workcities;
    }

    public void setWorkcities(City[] workcities) {
        this.workcities = workcities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", configFileLocation=" + configFileLocation +
                ", workcities=" + Arrays.toString(workcities) +
                ", lifeCities=" + lifeCities +
                '}';
    }

    public static User createUser(){
        return new User();
    }



}
