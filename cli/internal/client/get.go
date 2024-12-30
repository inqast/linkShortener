package client

import (
	"fmt"
	"io"

	"encoding/json"
	linkUtils "github.com/inqast/cli/internal/link"
)

type getResp struct {
	Link string `json:"link"`
}

func (s *service) Get(link string) (string, error) {
	hash, err := linkUtils.GetHashFromShortLink(link, s.shortLinkBase)
	if err != nil {
		return "", err
	}

	url := fmt.Sprintf("http://%s/link/%d", s.serverBase, hash)

	resp, err := s.c.Get(url)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)

	if resp.StatusCode != 200 {
		return "", fmt.Errorf("code %d: %s", resp.StatusCode, string(body))
	}

	r := &getResp{}
	if err = json.Unmarshal(body, r); err != nil {
		return "", err
	}

	return r.Link, nil
}
