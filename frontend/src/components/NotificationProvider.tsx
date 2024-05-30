import React, { createContext, useContext, useState, ReactNode } from 'react';
import Notification from './Notification'; // Assuming you have the Notification component in a separate file

interface INotificationContextType {
    showNotification: (message: any, severity?: 'error' | 'warning' | 'info' | 'success') => void;
}

const NotificationContext = createContext<INotificationContextType | undefined>(undefined);

interface NotificationProviderProps {
    children: ReactNode;
}

function NotificationProvider (props: { children: any }) {
    const [notification, setNotification] = useState<{ message: string; severity: 'error' | 'warning' | 'info' | 'success' } | null>(null);

    const showNotification = (message: any, severity: 'error' | 'warning' | 'info' | 'success' = 'error') => {
        setNotification({ message, severity });
    };

    const handleCloseNotification = () => {
        setNotification(null);
    };

    return (
        <NotificationContext.Provider value={{ showNotification }}>
            {props.children}
            <Notification
                open={!!notification}
                onClose={handleCloseNotification}
                message={notification?.message || ''}
                severity={notification?.severity || 'error'}
            />
        </NotificationContext.Provider>
    );
}

export const useNotification = (): INotificationContextType => {

    const context = useContext(NotificationContext);
    if (!context) {
        throw new Error('useNotification must be used within a NotificationProvider');
    }
    return context;
};

export default NotificationProvider;