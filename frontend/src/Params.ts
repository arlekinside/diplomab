const backend = 'http://localhost:8080';

const Params = {
    fetch: {
        backend: backend,
        login: `${backend}/users/login`,
        register: `${backend}/users/register`,
        user: {
            current: `${backend}/users`
        },
        mf: {
            incomes: `${backend}/mf/incomes`,
            expenses: `${backend}/mf/expenses`,
            recurring: {
                incomes: `${backend}/mf/recurring/incomes`,
                expenses: `${backend}/mf/recurring/expenses`,
            }
        },
        savings: '/savings'
    },
    path: {
        logout: `${backend}/logout`,
        register: '/register',
        login: '/login',
        mf: {
            n: '/mfun',
            one: '/mfuo',
            recurring: '/mfur'
        },
        savings: {
            n: '/sun',
            page: '/su'
        },
        dashboard: '/dashboard'
    },
    colors: {
        primary: '#16348C', //FFD700 //16348C
        secondary: '#E16941',
        thirdly: '#7894EA',
        background: '#ffffff',
        darkText: '#000000',
        lightText: '#ffffff',
    },
    labels: {
        appName: 'MFT'
    },
    cookies: {
        uname: 'un'
    }
}


export default Params;