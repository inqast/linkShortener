package client

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/inqast/cli/internal/short"
)

type updateReq struct {
	Hash     int    `json:"hash"`
	User     string `json:"user"`
	Link     string `json:"link,omitempty"`
	Limit    string `json:"limit,omitempty"`
	Deadline string `json:"deadline,omitempty"`
}

func (s *service) Update(
	shortLink string, user string,
	targetLink string,
	limit string, deadline string,
) error {
	hash, err := short.GetHashFromShortLink(shortLink, s.shortLinkBase)
	if err != nil {
		return err
	}

	url := fmt.Sprintf("http://%s/link/", s.serverBase)

	reqBytes, err := json.Marshal(updateReq{
		Hash:     hash,
		User:     user,
		Link:     targetLink,
		Limit:    limit,
		Deadline: deadline,
	})
	if err != nil {
		return err
	}

	req, err := http.NewRequest(http.MethodPatch, url, bytes.NewReader(reqBytes))
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
