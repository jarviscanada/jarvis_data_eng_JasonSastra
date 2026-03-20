FROM ubuntu:22.04

# install required tools
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    curl \
    git \
    docker.io \
    && rm -rf /var/lib/apt/lists/*

# install kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" \
    && chmod +x kubectl \
    && mv kubectl /usr/local/bin/

# ensure docker daemon can run
RUN mkdir /var/run/docker.sock

# use bash by default
CMD ["bash"]