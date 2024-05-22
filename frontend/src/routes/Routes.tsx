import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import DashboardPage from "../pages/DashboardPage";
import NewMoneyFlowPage from "../pages/mf/NewMoneyFlowPage";
import OneTimeMoneyFlowPage from "../pages/mf/OneTimeMoneyFlowPage";
import RecurringMoneyFlowPage from "../pages/mf/RecurringMoneyFlowPage";
import NewSavingPage from "../pages/saving/NewSavingPage";
import SavingPage from "../pages/saving/SavingPage";

const Routes = {
    '/' : () => <DashboardPage />,
    '/dsb': () => <DashboardPage />,
    '/login' : () => <LoginPage />,
    '/register' : () => <RegisterPage />,
    '/mfun' : () => <NewMoneyFlowPage />,
    '/mfuo' : () => <OneTimeMoneyFlowPage />,
    '/mfur' : () => <RecurringMoneyFlowPage />,
    '/sun' : () => <NewSavingPage />,
    '/su' : () => <SavingPage />
}

export default Routes;