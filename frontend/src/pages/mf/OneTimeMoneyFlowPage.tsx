import Page from "../../components/layout/Page";
import {useEffect} from "react";
import {useNotification} from "../../components/NotificationProvider";

function OneTimeMoneyFlowPage() {

    const {showNotification} = useNotification();

    useEffect(() => {
        fetch('/mf/income', {
            method: 'GET',
            redirect: 'follow'
        }).then(res => {
            if (!res.status) {
                showNotification('err')
                throw new Error(`Got unexpected response ${res.status} from server`);
            }
            return res.json();
        }).then(json => {
            alert('j');
            // showNotification(json, 'success');
        }).catch((...e) => {
            console.log(...e);
            alert('e');
            // showNotification(e);
        })
    }, []);

    return (
        <Page>
            <div/>
        </Page>
    );
}

export default OneTimeMoneyFlowPage;