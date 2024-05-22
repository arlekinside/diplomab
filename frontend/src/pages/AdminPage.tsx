import {useEffect, useState} from "react";
import Page from "../components/layout/Page";

interface Data {
    correctness: number;
    usersCount: number;
    health: boolean
}

function AdminPage() {

    const [fetched, setFetched] = useState<boolean>(false);
    const [username, setUsername] = useState<string>('SQLver');
    const [data, setData] = useState<Data>({
        correctness: 100,
        usersCount: 9999,
        health: true
    });

    useEffect(() => {
        if (!fetched) {
            const fetchData = async () => {
                try {
                    const healthResponse = await fetch("/health");
                    let health = true
                    if (!healthResponse.ok) {
                        health = false
                    }

                    const statsResponse = await fetch("/history/stats");
                    let correctness = 0;
                    if (statsResponse.ok) {
                        correctness = (await statsResponse.json()).correctness;
                    }

                    const usersResponse = await fetch("/users/stats");
                    let usersCount = 0;
                    let username = 'SQLver';
                    if (usersResponse.ok) {
                        let usersData = await usersResponse.json();
                        usersCount = usersData.usersCount;
                        username = usersData.username;
                    }

                    setData({
                        correctness: correctness,
                        usersCount: usersCount,
                        health: health,
                    });

                    setUsername(username);
                } catch (error) {
                    // Handle errors here
                    console.error("Error fetching data:", error);
                }
            };

            fetchData();
            setFetched(true);
        }
    }, [fetched]);

    return (
        <Page admin>
            <div/>
        </Page>
    );
}

export default AdminPage;