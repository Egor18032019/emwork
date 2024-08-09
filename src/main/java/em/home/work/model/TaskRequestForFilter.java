package em.home.work.model;

import em.home.work.utils.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestForFilter {
    String name;
    Priority filterPriority;
    String filterContractor;
    String filterDescription;

    @Override
    public String toString() {
        return "TaskRequestForFilter{" +
                "name='" + name + '\'' +
                ", filterPriority='" + filterPriority + '\'' +
                ", filterContractor='" + filterContractor + '\'' +
                ", filterDescription='" + filterDescription + '\'' +
                '}';
    }
}
