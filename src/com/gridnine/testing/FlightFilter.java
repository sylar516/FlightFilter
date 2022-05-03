package com.gridnine.testing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlightFilter {
    private List<Flight> flights;
    private Map<String, Filter> rulesFiltration = new HashMap<>();

    //метод для добавления правила фильтрации перелётов в набор правил
    public void addFilter(String description, Filter filter) {
        rulesFiltration.put(description, filter);
    }

    //метод для получения набора правил фильтрации перелётов в виде множества строк(ключей)
    public Set<String> getRulesFiltration() {
        return rulesFiltration.keySet();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public FlightFilter(List<Flight> flights) {
        this.flights = flights;
    }

    //метод фильтрации набора перелётов при помощи динамически заданного правила
    public List<Flight> setAndExecuteFilter(String desription, Filter filter) {
        if (filter == null) {
            System.out.println("Правило фильтрации не задано, набор перелётов не изменён");
            return flights;
        }
        System.out.println("Исключены перелёты, удовлетворяющие правилу - " + desription + ":");
        return filter.filter(flights);
    }

    //метод фильтрации набора перелётов при помощи динамически выбранного правила
    public List<Flight> selectAndExecuteFilter(String description) {
        if (rulesFiltration.containsKey(description)) {
            System.out.println("Исключены перелёты, удовлетворяющие правилу - " + description + ":");
            return rulesFiltration.get(description).filter(flights);
        }
        System.out.println("Правило фильтрации не найдено, набор перелётов не изменён");
        return flights;
    }
}
