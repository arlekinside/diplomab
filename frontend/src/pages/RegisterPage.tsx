import { Button, TextField } from "@mui/material";
import { useState } from "react";
import Content from "../components/Content";
import DarkText from "../components/text/DarkText";
import DarkTitle from "../components/text/DarkTitle";

interface FormInput {
    username: string;
    password: string;
    verifyPassword: string;
}

interface Request {
    username: string;
    password: string;
}

function RegisterPage() {

    const [formInput, setFormInput] = useState<FormInput>({
        username: '',
        password: '',
        verifyPassword: '',
    });

    const [unameHelp, setUnameHelp] = useState<string|undefined>();
    const [passHelp, setPassHelp] = useState<string|undefined>();
    const [vPassHelp, setVPassHelp] = useState<string|undefined>();

    const resetErrors = () => {
        setUnameHelp(undefined);
        setPassHelp(undefined);
        setVPassHelp(undefined);
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        resetErrors();

        let username = formInput.username;
        let pass = formInput.password;
        let vPass = formInput.verifyPassword;

        if(pass.length < 5) {
            setPassHelp("Password < 5 chars");
        }

        if (vPass !== pass) {
            setVPassHelp("Passwords do not match");
            return;
        }

        let req: Request = {
            username: username,
            password: pass
        }

        fetch(`/users/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(req)
        }).then(res => {
            if(!res.ok) {
                setUnameHelp('Username exists');
                return;
            }
            window.location.pathname = '/login'
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
                            Register
                        </DarkText>
                        <TextField name="username" id="username" label="Username" variant="outlined" margin="normal" onChange={handleChange} error={unameHelp !== undefined} helperText={unameHelp}/>
                        <TextField name="password" id="password" label="Password" variant="outlined" margin="normal" type="password" onChange={handleChange} error={passHelp !== undefined} helperText={passHelp}/>
                        <TextField name="verifyPassword" id="verifyPassword" label="Verify password" variant="outlined" margin="normal" type="password" onChange={handleChange} error={vPassHelp !== undefined} helperText={vPassHelp}/>
                        <div>
                            <Button size={"large"} type={"submit"} variant="contained" style={{ margin: "3vh" }}>
                                Register
                            </Button>
                            <a href="/login">
                                <Button size={"large"} variant="outlined" style={{ margin: "3vh" }}>
                                    Login
                                </Button>
                            </a>
                        </div>
                    </div>
                </form>
            </Content>
        </div>
    );
}

export default RegisterPage;