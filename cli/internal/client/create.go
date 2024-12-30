package client

import (
	"bytes"
	"fmt"
	"io"

	"encoding/json"
	linkUtils "github.com/inqast/cli/internal/link"
)

type createReq struct {
	Link     string `json:"link"`
	User     string `json:"user,omitempty"`
	Limit    string `json:"limit,omitempty"`
	Deadline string `json:"deadline,omitempty"`
}

type createResp struct {
	Hash int    `json:"hash"`
	User string `json:"user"`
}

func (s *service) Create(
	link string, user string,
	limit string, deadline string,
) (string, string, error) {
	url := fmt.Sprintf("http://%s/link/", s.serverBase)

	req := createReq{
		Link:     link,
		User:     user,
		Limit:    limit,
		Deadline: deadline,
	}

	reqBytes, err := json.Marshal(req)
	if err != nil {
		return "", user, err
	}

	resp, err := s.c.Post(url, "application/json", bytes.NewReader(reqBytes))
	if err != nil {
		return "", user, err
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)

	if resp.StatusCode != 200 {
		return "", user, fmt.Errorf("code %d: %s", resp.StatusCode, string(body))
	}

	r := &createResp{}
	if err = json.Unmarshal(body, r); err != nil {
		return "", user, err
	}

	return linkUtils.CreateShortLink(s.shortLinkBase, r.Hash), r.User, nil
}
