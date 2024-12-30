package client

import (
	"errors"
	"fmt"
	"io"

	"encoding/json"
	"github.com/inqast/cli/internal/model"
)

type getAllResp struct {
	Links []model.Link `json:"links"`
}

func (s *service) GetAll(uuid string) ([]model.Link, error) {

	url := fmt.Sprintf("http://%s/links/%s", s.serverBase, uuid)

	resp, err := s.c.Get(url)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)

	if resp.StatusCode != 200 {
		return nil, errors.New(string(body))
	}

	r := &getAllResp{}
	if err = json.Unmarshal(body, r); err != nil {
		return nil, err
	}

	return r.Links, nil
}
