package link

import (
	"errors"
	"fmt"
	"os/exec"
	"runtime"
	"strconv"
	"strings"
)

func GetHashFromShortLink(shortLink string, linkBase string) (int, error) {
	shortLink = strings.TrimPrefix(shortLink, "http://")
	shortLink = strings.TrimPrefix(shortLink, "https://")

	if len(shortLink) <= len(linkBase) {
		return 0, errors.New("incorrect link")
	}

	base, hashStr := shortLink[:len(linkBase)], shortLink[len(linkBase)+1:]
	if base != linkBase {
		return 0, errors.New("incorrect link")
	}

	hash, err := strconv.Atoi(hashStr)
	if err != nil {
		return 0, err
	}

	return hash, nil
}

func CreateShortLink(linkBase string, hash int) string {
	return fmt.Sprintf("http://%s/%d", linkBase, hash)
}

func OpenLink(url string) error {
	var cmd string
	var args []string

	switch runtime.GOOS {
	case "windows":
		cmd = "cmd"
		args = []string{"/c", "start"}
	case "darwin":
		cmd = "open"
	default: // "linux", "freebsd", "openbsd", "netbsd"
		cmd = "xdg-open"
	}
	args = append(args, url)
	return exec.Command(cmd, args...).Start()
}
