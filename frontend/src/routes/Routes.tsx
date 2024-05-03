import AdminPage from "../pages/AdminPage";
import HomePage from "../pages/HomePage";
import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import VerifyPage from "../pages/VerifyPage";

const Routes = {
    '/' : () => <VerifyPage />,
    '/admin' : () => <AdminPage />,
    '/home' : () => <HomePage />,
    '/login' : () => <LoginPage />,
    '/register' : () => <RegisterPage />
}

export default Routes;