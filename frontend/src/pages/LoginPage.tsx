import { Button, TextField } from "@mui/material";
import { useState } from "react";
import Params from "../Params";
import Content from "../components/Content";
import DarkText from "../components/text/DarkText";
import DarkTitle from "../components/text/DarkTitle";

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

    const [unameHelp, setUnameHelp] = useState<string|undefined>(params.get('error') ? 'Username/password is wrong' : undefined);

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
            if(res.redirected) {
                window.location.href = res.url;
            }
        }).catch(e => {
            console.error('Error while fetching', e);
            alert('Error connecting to server');
        })
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormInput({
            ...formInput,
            [name]: value,
        });
    };

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "flex-start",
            minWidth: "100%",
            alignItems: "center"
        }}>
            <Content>
                <form onSubmit={handleSubmit}>
                    <div style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "flex-start",
                        minWidth: "100%",
                        alignItems: "center"
                    }}>
                        <DarkTitle>
                            SQLver
                        </DarkTitle>
                        <DarkText>
                            Login
                        </DarkText>
                        <TextField id="username" name="username" label="Username" variant="outlined" margin="normal" onChange={handleChange} helperText={unameHelp}/>
                        <TextField id="password" name="password" label="Password" variant="outlined" margin="normal" type="password" onChange={handleChange}/>
                        <div>
                            <Button size={"large"} type={"submit"} variant="contained" style={{margin: "3vh"}}>
                                Login
                            </Button>
                            <a href="/register">
                                <Button size={"large"} variant="outlined" style={{margin: "3vh"}}>
                                    Register
                                </Button>
                            </a>
                        </div>
                    </div>
                </form>
            </Content>
        </div>
    );
}

export default LoginPage;