package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowConverter;
import actions.views.FollowView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Employee;
import models.Follow;
import models.Report;

public class AccountService  extends ServiceBase {

    /**
     * 指定した従業員がフォローした従業員データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return
     */
    public List<FollowView> getMinePerPage(EmployeeView employee, int page) {
        List<Follow> follows = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL_MINE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return FollowConverter.toViewList(follows);
    }

    /**
     * 指定した従業員がフォローした従業員データの件数を取得し、返却する
     * @param employee
     * @return フォローデータの件数
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_FOL_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getUserPerPage(EmployeeView employee, int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_ONE, Report.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * 指定した従業員が作成した日報データの件数を取得し、返却する
     * @param report
     * @return 日報データの件数
     */
    public long countAllOne(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_ONE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定した従業員をフォローする
     * @param employee
     */
    public void newFollow (FollowView fv) {

        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fv));
        em.getTransaction().commit();
    }

    public void remove (Employee loginEmp, Employee employee) {

        int id = (int) em.createNamedQuery(JpaConst.Q_FOL_GET_KEY, Integer.class)
                            .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, loginEmp)
                            .setParameter(JpaConst.JPQL_PARM_FOLLOW, employee)
                            .getSingleResult();

        Follow f = folFindOneInternal(id);

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
    }

    /**
     * idを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public EmployeeView empFindOne(int id) {
        Employee e = empFindOneInternal(id);
        return EmployeeConverter.toView(e);
    }

    public Employee empFindOneInternal(int id) {
        Employee e = em.find(Employee.class, id);

        return e;
    }

    public ReportView repFindOne(int id) {
        Report r = repFindOneInternal(id);
        return ReportConverter.toView(r);
    }

    public Report repFindOneInternal(int id) {
        Report r = em.find(Report.class, id);

        return r;
    }

    public FollowView folFindOne(int id) {
        Follow f = folFindOneInternal(id);
        return FollowConverter.toView(f);
    }

    public Follow folFindOneInternal(int id) {
        Follow f = em.find(Follow.class, id);

        return f;
    }

    public boolean findFollow(FollowView loginEmp, FollowView fv) {

        Follow f = null;

        f = em.createNamedQuery(JpaConst.Q_FOL_GET_FOLLOW, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, loginEmp)
                .setParameter(JpaConst.JPQL_PARM_FOLLOW, fv)
                .getSingleResult();

        if(f != null) {
            return true;
        } else {
            return false;
        }
    }
}
