import React, { Component } from 'react'
import { Button, Form, Grid, Segment } from 'semantic-ui-react'

import AuthenticationService from '../auth/AuthenticationService';

class PageLogin extends Component {

    constructor(props) {
        super(props);

        this.state = {
            loginOK: false,
            username: "",
            password: ""
        }
    }

    handleChange = (e, {value}) => {
        if (e.target.id == "username") { this.setState({username: value})}
        if (e.target.id == "password") { this.setState({password: value})}
    }

    loginClicked = (e, {value}) => {
        AuthenticationService.executeJwtAuthenticationService(this.state.username, this.state.password)
        .then((response) => {
            AuthenticationService.registerSuccessfulLoginForJwt(this.state.username, response.data.token)
        this.props.history.push("/user-panel");
    }).catch(() => {
        console.log("FAIL!")
    })
    }

    render() {
        return (
            <div className="ui-login-panel">
                <Segment placeholder>
                    <Grid columns={1} relaxed='very'>
                        <Grid.Column>
                            <Form>
                                <Form.Input label="Username" id="username" placeholder="Username" onChange={this.handleChange} icon="user"/>
                                <Form.Input label="Password" id="password" placeholder="Password" onChange={this.handleChange} icon="lock" type="password"/>
                                <Button content='Login' onClick={this.loginClicked} primary />
                            </Form>
                        </Grid.Column>
                    </Grid>
                </Segment>
            </div>
        )
    }
}

export default PageLogin;