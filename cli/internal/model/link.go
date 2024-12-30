package model

type Link struct {
	Hash       int    `json:"hash"`
	Link       string `json:"link"`
	Owner      string `json:"owner"`
	UsageCount int    `json:"usages"`
	UsageLimit int    `json:"limit"`
	Deadline   string `json:"deadline"`
}
