package client

import (
	"io"
	"net/http"
)

type (
	client interface {
		Get(url string) (resp *http.Response, err error)
		Post(url, contentType string, body io.Reader) (resp *http.Response, err error)
		Do(req *http.Request) (*http.Response, error)
	}

	service struct {
		shortLinkBase string
		serverBase    string
		c             client
	}
)

func New(shortLinkBase string, serverBase string, c client) *service {
	return &service{
		shortLinkBase: shortLinkBase,
		serverBase:    serverBase,
		c:             c,
	}
}
