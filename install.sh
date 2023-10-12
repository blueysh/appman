# install script for appman
echo "Installing appman"

cd /usr/local/bin/

#curl -O https://github.com/blueysh/appman/releases/download/release/appman
wget https://github.com/blueysh/appman/releases/download/release/appman

chmod +x /usr/local/bin/appman

echo "info - appman has been installed to /usr/local/bin/"
