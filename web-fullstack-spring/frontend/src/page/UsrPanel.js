import React, { Component } from 'react'

import { Segment, Menu } from 'semantic-ui-react'

import UsrImageUploader from './UsrImageUploader'
import UsrImageTable from './UsrImageTable'

class UsrPanel extends Component {

    render() {
        //---- Obtain user name from session/cookies
        const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
        const username = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        var location = this.props.location;

        var html = ""
        if (location == "collection") { html = (<UsrImageTable/>);}
        if (location == "upload") { html = (<UsrImageUploader/>);}

        return (
            <div className="user-panel">
                <Menu color="gray">
                    <Menu.Item header>{username}</Menu.Item>
                    <Menu.Item name="collection"><a href="/collection">Collection</a></Menu.Item>
                    <Menu.Item name="upload"><a href="/upload">Upload</a></Menu.Item>
                </Menu>
                <Segment>{html}</Segment>
            </div>
        )
    }
}

export default UsrPanel;
