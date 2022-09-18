import React, { Component } from 'react'
import { Header, Table, Button } from 'semantic-ui-react'
import axios from 'axios'

import Settings from '../settings/Settings'

class UsrImageUploader extends Component {

  fileInputRef = React.createRef();

  constructor(props) {
    super(props);

         var tck = sessionStorage.getItem("TOKEN")
        axios.interceptors.request.use((config) => {
                config.headers.authorization = tck;
                return config;
            }
        )

    this.state = {
      file: "",
      fileName: "not selected",
      isdone: false
    };
  }

  fileChange = (e) => {
      this.setState({ file: e.target.files[0] })
      this.setState({fileName: e.target.files[0].name})
  };


  onClickUpload = (e) => {
      let data = new FormData();

      data.append('file', this.state.file);
      data.append('name', this.state.fileName);

      const config = { headers: { 'Content-Type': 'multipart/form-data' } };

      axios.post(Settings.API_USR_IMAGE_UPLOAD, data, config).then(response => {
        this.setState({isdone: true})})
  }

  render() {
    return (
        <div>
            <Header>New Task</Header>
                <div style={{width: "500px"}}>
                <Table>
                    <Table.Body>
                        <Table.Row>
                        <Table.Cell>Input file</Table.Cell>
                        <Table.Cell>{this.state.fileName}</Table.Cell>
                        <Table.Cell>
                        <Button content="Choose File" labelPosition="left" icon="file" floated="right" onClick={() => this.fileInputRef.current.click()} color="grey"/>
                        <input  ref={this.fileInputRef}  type="file" hidden onChange={this.fileChange}/>
                        </Table.Cell>
                        </Table.Row>
                    </Table.Body>

                     <Table.Footer fullWidth>
                          <Table.Row>
                            <Table.HeaderCell colSpan='3'>
                            <Button color="blue" floated="right" onClick={this.onClickUpload}>Upload</Button>
                            </Table.HeaderCell>
                          </Table.Row>
                     </Table.Footer>

                </Table>
                </div>
            </div>

        )
    }

}


export default UsrImageUploader;