package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.AccountService;

public class AccountAction extends ActionBase {

    private AccountService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new AccountService();

        // メソッドを実行
        invoke();
        service.close();
    }

    /**
     *  フォロー一覧画面を表示する
     *  @throws ServletException
     *  @throws IOException
     */
    public void followList() throws ServletException, IOException {

        // ログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // ログイン中の従業員がフォローした従業員を、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<FollowView> follows = service.getMinePerPage(loginEmployee, page);

        // ログイン中の従業員がフォローした従業員の件数を取得
        long myFollowsCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.FOLLOWS, follows); // 取得したフォローデータ
        putRequestScope(AttributeConst.FOL_COUNT, myFollowsCount); // ログイン中の従業員がフォローした従業員の数
        putRequestScope(AttributeConst.PAGE, page); // ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); // 1ページに表示するレコードの件数

        // フォロー一覧画面を表示
        forward(ForwardConst.FW_ACC_FOLLOW);
    }

    public void account() throws ServletException, IOException {

        // idを条件に従業員データを取得する
        EmployeeView ev = service.empFindOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

        if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

         // データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }



        putRequestScope(AttributeConst.EMPLOYEE, ev); // 取得した従業員情報

        // 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = service.getUserPerPage(ev, page);

        // 指定した従業員が作成した日報データの件数を取得
        long userReportCount = service.countAllOne(ev);

        putRequestScope(AttributeConst.REPORTS, reports); // 取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, userReportCount); // 指定した従業員が作成した日報の件数
        putRequestScope(AttributeConst.PAGE, page); // ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); // 1ページに表示するレコードの件数

        // アカウント画面を表示
        forward(ForwardConst.FW_ACC_ACCOUNT);

    }

    private boolean checkAdmin() throws ServletException, IOException {

        // セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // 管理者でなければエラー画面を表示
        if (ev.getAdminFlag() != AttributeConst.ROLE_ADMIN.getIntegerValue()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;

        } else {

            return true;
        }

    }

    public void newFollow() throws ServletException, IOException {

        FollowView fv = new FollowView(
                null,
                (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP),
                service.empFindOne(toNumber(getRequestParam(AttributeConst.EMP_ID))));

        service.newFollow(fv);

        redirect(ForwardConst.ACT_ACC, ForwardConst.CMD_ACCOUNT, service.empFindOne(toNumber(getRequestParam(AttributeConst.EMP_ID))));
    }

    public void remove() throws ServletException, IOException {

        FollowView fv = new FollowView(
                null,
                (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP),
                service.empFindOne(toNumber(getRequestParam(AttributeConst.EMP_ID))));


        service.remove(fv);

        redirect(ForwardConst.ACT_ACC, ForwardConst.CMD_ACCOUNT, service.empFindOne(toNumber(getRequestParam(AttributeConst.EMP_ID))));
    }

}
