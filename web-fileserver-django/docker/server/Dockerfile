FROM python:3.8

# ---------------------------------------------------------
# ---- Supress warnings about the missing frontend
ARG DEBIAN_FRONTEND=noninteractive

# ---------------------------------------------------------
# ---- Essentials: developer tools, build tools, OpenBLAS


# ---------------------------------------------------------
# ---- Python 3.8
#RUN apt install python3.8 -y
#RUN ln -s /usr/bin/pip3 /usr/bin/pip && \
#    ln -s /usr/bin/python3.8 /usr/bin/python
#RUN apt-get install python3-pip -y

# ---------------------------------------------------------
RUN apt-get install -y --no-install-recommends libjpeg-dev zlib1g-dev

# ---------------------------------------------------------

# ---------------------------------------------------------
# ---- JAVA
#RUN apt-get install -y --no-install-recommends default-jdk

# ---------------------------------------------------------
ADD ./docker/server/requirements.txt ./
RUN pip3 install --upgrade pip
RUN pip3 install --no-cache-dir -r requirements.txt

RUN mkdir app
RUN mkdir app/static
RUN mkdir app/static/image
WORKDIR app

COPY ./server ./server


# ---------------------------------------------------------
RUN export LC_ALL=en_US.utf-8
RUN export LANG=en_US.utf-8

