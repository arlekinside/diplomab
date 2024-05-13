import {Button, TextField} from "@mui/material";
import {useState} from "react";
import Title from "../components/text/Title";
import Page from "../components/layout/Page";
import Params from "../Params";

interface FormInput {
    username: string;
    password: string;
}

function LoginPage() {

    const params = new URLSearchParams(window.location.search);

    const [formInput, setFormInput] = useState<FormInput>({
        username: '',
        password: ''
    });

    const [unameHelp, setUnameHelp] = useState<string | undefined>(params.get('error') ? 'Username/password is wrong' : undefined);

    const resetErrors = () => {
        setUnameHelp(undefined);
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        resetErrors();

        const username = formInput.username;
        const pass = formInput.password;

        fetch(`/users/login?username=${username}&password=${pass}`, {
            method: 'POST'
        }).then(res => {
            if (res.redirected) {
                window.location.href = res.url;
            }
        }).catch(e => {
            console.error('Error while fetching', e);
            alert('Error connecting to server');
        })
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setFormInput({
            ...formInput,
            [name]: value,
        });
    };

    return (
        <Page width='50%' unauthorized>
            <form onSubmit={handleSubmit}>
                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "flex-start",
                    minWidth: "100%",
                    alignItems: "center"
                }}>
                    <Title>
                        Login
                    </Title>
                    <TextField id="username" name="username" label="Username" variant="outlined" margin="normal"
                               onChange={handleChange} helperText={unameHelp}/>
                    <TextField id="password" name="password" label="Password" variant="outlined" margin="normal"
                               type="password" onChange={handleChange}/>
                    <div>
                        <Button href={Params.path.register} size={"large"} variant="outlined" style={{margin: "3vh"}}>
                            Register
                        </Button>
                        <Button size={"large"} type={"submit"} variant="contained" style={{margin: "3vh"}}>
                            Login
                        </Button>
                    </div>
                </div>
            </form>
        </Page>
    );
}

export default LoginPage;