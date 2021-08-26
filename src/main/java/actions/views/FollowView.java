package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowView {

    private Integer id;

    /**
     * フォローする側の従業員id
     */
    private EmployeeView employee;

    /**
     * フォローされる側の従業員id
     */
    private EmployeeView follow;
}
