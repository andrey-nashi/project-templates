import React, { Component } from 'react'

import AuthenticationService from '../auth/AuthenticationService';

class PageLogout extends Component {

    componentDidMount() {
        AuthenticationService.logout();
        this.props.history.push('/home')

    }

    render() {
        return (
            <div className="dummy">

            </div>
        )
    }
}

export default PageLogout;