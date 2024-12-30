/*
Copyright © 2024 NAME HERE <EMAIL ADDRESS>
*/
package cmd

import (
	"fmt"
	"net/http"

	"github.com/inqast/cli/internal"
	"github.com/inqast/cli/internal/client"
	"github.com/spf13/cobra"
)

// updateCmd represents the update command
var updateCmd = &cobra.Command{
	Use:       "update",
	Short:     "update created link, requires login",
	ValidArgs: []string{"shortLink"},
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) != 1 {
			fmt.Printf("missing argument: shortLink")
			return
		}

		user, _ := cmd.Flags().GetString(userKey)

		target, _ := cmd.Flags().GetString(targetKey)
		limit, _ := cmd.Flags().GetString(limitKey)
		deadline, _ := cmd.Flags().GetString(deadlineKey)

		c := client.New(internal.LinkBase, internal.ServerAddr, &http.Client{})

		err := c.Update(
			args[0],
			user,
			target,
			limit,
			deadline,
		)
		if err != nil {
			fmt.Println(err.Error())
			return
		}
	},
}

func init() {
	rootCmd.AddCommand(updateCmd)

	updateCmd.PersistentFlags().String(targetKey, "", "target link to redirect")
}
