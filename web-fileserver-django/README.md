---------------------------------------
**Requirements**
- OS - Ubuntu/Mint 20.04 recommended 
- Docker (tested on Docker version 20.10.12, build 20.10.12-0ubuntu2~20.04.1)
- Python 3.8
---------------------------------------
**Deployment**
- Server deployment
  - Go to `docker` directory
  - Build docker images `docker-compose build`
  - Start server `docker-compose up`
- Client 
  - Go to `client` directory
  - Execute `python3 run.py` and read the help info
  - Use files in directory to test the functionality (by default the target IP is 0.0.0.0:8000)
  - Usage Examples
    - `get` - will list files on the server (see mounted volume docker/static/image)
      (should initially show 1 file `cute-cat.jpg`)
    - `upload fluffy-cat.jpg` - will upload the image of the fluffy siberian cat to the server
      (the db of images will be updated - file docker/static/db.sqlite3)
    - `delete cute-cat.jpg` - will delete image of the grumpy cat
    - `delete no-file` - attempting to delete a file not on the server will cause error message to be displayed
    - `upload no-file-found` - attempting to upload a non-existent file will cause error message to be displayed
---------------------------------------
**Testing**
- Initialize default DB state (just copy server/db.sqlite3->docker/static/db.sqlite3)
- Run `python3 run-test.py` in the `test` directory
---------------------------------------