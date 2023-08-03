# Needs to be started with parameter --privileged so docker can run network interfaces
FROM archlinux:latest
ARG VERSION=1.0.3

# Update the package list and upgrade the system
RUN pacman -Syu --noconfirm

# Install Asciinema
RUN pacman -S --noconfirm xorg-server-xvfb
RUN pacman -S --noconfirm terminator
RUN pacman -S --noconfirm asciinema

# Install Java
RUN pacman -S --noconfirm jdk17-openjdk
RUN pacman -S --noconfirm maven

# Install Node.js and npm
RUN pacman -S --noconfirm nodejs npm
RUN npm install -g npm@latest

# Install Go
RUN pacman -S --noconfirm go

# Install Python 3
RUN pacman -S --noconfirm python python-pip python-pipx

# Install GIT
RUN pacman -S --noconfirm git

# Install Docker dependencies
RUN pacman -S --noconfirm iptables libseccomp fuse-overlayfs

# Install Docker client
RUN pacman -S --noconfirm docker

# Set environment variables for Java, Node.js, Go, Python, and Docker
ENV JAVA_HOME=/usr/lib/jvm/default
ENV PATH=$PATH:/usr/lib/jvm/default/bin:/usr/lib/go/bin:/usr/bin:/usr/lib/docker/cli/bin

# Set environment variable for Node.js
ENV PATH=$PATH:/usr/bin/node

# Install Syft
RUN curl -sSfL https://raw.githubusercontent.com/anchore/syft/main/install.sh | sh -s -- -b /usr/local/bin

# Install Trivy
RUN pacman -S --noconfirm trivy

# Install CyclondeDx
RUN npm install -g @cyclonedx/cdxgen
RUN npm install -g @cyclonedx/cdxgen-plugins-bin

# Install Kubernetes sigs SBOM
RUN go install sigs.k8s.io/bom/cmd/bom@latest
RUN cp /root/go/bin/bom /usr/local/bin/bom

# Install Tern
RUN pacman -S --noconfirm jq
RUN pacman -S --noconfirm skopeo
RUN pipx install tern
RUN pipx ensurepath

# Install ScanCode.io
RUN pipx install scancode-toolkit

# Install microsoft sbom tool
RUN curl -Lo /usr/local/bin/sbom-tool https://github.com/microsoft/sbom-tool/releases/latest/download/sbom-tool-linux-x64
RUN chmod +x /usr/local/bin/sbom-tool

# Install CycloneDx CLI
RUN curl -Lo /usr/local/bin/cyclonedx-cli https://github.com/CycloneDX/cyclonedx-cli/releases/download/v0.24.2/cyclonedx-linux-x64
RUN chmod +x /usr/local/bin/cyclonedx-cli

# Install Scanners
RUN curl -sSfL https://raw.githubusercontent.com/anchore/grype/main/install.sh | sh -s -- -b /usr/local/bin
RUN pacman -S --noconfirm osv-scanner

# Set the working directory
WORKDIR /app

# Start docker deamon
#CMD dockerd