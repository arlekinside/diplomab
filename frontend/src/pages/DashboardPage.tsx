import Page from "../components/layout/Page";
import {useEffect, useState} from "react";
import Params from "../Params";
import {useNotification} from "../components/NotificationProvider";

function DashboardPage() {

    const [username, setUsername] = useState();
    const {showNotification} = useNotification();

    useEffect(() => {
        fetch(Params.fetch.user.current, {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(json => {
                showNotification(json);
            })
    }, []);

    return(
      <Page>
      </Page>
    );
}

export default DashboardPage;