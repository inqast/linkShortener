package short

import (
	"errors"
	"fmt"
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
