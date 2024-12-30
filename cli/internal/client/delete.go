package client

import (
	"bytes"
	"fmt"
	"io"
	"net/http"

	"encoding/json"
	"github.com/inqast/cli/internal/link"
)

type deleteReq struct {
	Hash int    `json:"hash"`
	User string `json:"user"`
}

func (s *service) Delete(
	shortLink string, user string,
) error {
	hash, err := link.GetHashFromShortLink(shortLink, s.shortLinkBase)
	if err != nil {
		return err
	}

	url := fmt.Sprintf("http://%s/link/", s.serverBase)

	reqBytes, err := json.Marshal(deleteReq{
		Hash: hash,
		User: user,
	})
	if err != nil {
		return err
	}

	req, err := http.NewRequest(http.MethodDelete, url, bytes.NewReader(reqBytes))
	if err != nil {
		return err
	}

	resp, err := s.c.Do(req)
	if err != nil {
		return err
	}
	defer resp.Body.Close()

	if resp.StatusCode != 200 {
		body, _ := io.ReadAll(resp.Body)
		return fmt.Errorf("code %d: %s", resp.StatusCode, string(body))
	}

	return nil
}
