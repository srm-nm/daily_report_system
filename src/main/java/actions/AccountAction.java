package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
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
    public void follow() throws ServletException, IOException {

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
        forward(ForwardConst.FW_FOL_FOLLOW);
    }

}
