import React, { Component } from 'react'
import { Header, Table, Button } from 'semantic-ui-react'
import axios from 'axios'

import Settings from '../settings/Settings'
import UsrImage from '../cmp/UsrImage'

class UsrImageTable extends Component {

    //-------------------------------------
    fetchUserData() {
        axios.get(Settings.API_USR_IMAGE_TBL).then(response => {
        this.setState({data:response.data})})
    }

    //-------------------------------------

    constructor(props) {
        super(props)
        var tck = sessionStorage.getItem("TOKEN")
        axios.interceptors.request.use((config) => {
                config.headers.authorization = tck;
                return config;
            }
        )

        this.state = {
            data: []
        }
    }

    componentDidMount() {
        this.fetchUserData()
    }

    render() {

        const htmlTable =  (this.state.data).map(element => {
                    return (
                         <Table.Row>
                            <Table.Cell><UsrImage id={element.id}/></Table.Cell>
                            <Table.Cell>{element.id}</Table.Cell>
                            <Table.Cell>{element.name}</Table.Cell>
                            <Table.Cell><Button color="grey">Delete</Button></Table.Cell>
                         </Table.Row>
                           )
                         });

        return (
            <div>
                <Header>Task History</Header>
                <Table celled padded>
                    <Table.Header>
                        <Table.Row>
                                <Table.HeaderCell>Image</Table.HeaderCell>
                                <Table.HeaderCell>Image id</Table.HeaderCell>
                                <Table.HeaderCell>Image name</Table.HeaderCell>
                                <Table.HeaderCell>Remove</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                <Table.Body>
                {htmlTable}
                </Table.Body>
                </Table>
            </div>
        )
    }
}

export default UsrImageTable;