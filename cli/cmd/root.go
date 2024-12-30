package cmd

import (
	"os"

	"github.com/spf13/cobra"
)

const (
	targetKey   = "target"
	userKey     = "user"
	limitKey    = "limit"
	deadlineKey = "deadline"
)

// rootCmd represents the base command when called without any subcommands
var (
	rootCmd = &cobra.Command{
		Use:   "cli",
		Short: "cli for linkShortener",
	}
)

// Execute adds all child commands to the root command and sets flags appropriately.
// This is called by main.main(). It only needs to happen once to the rootCmd.
func Execute() {
	err := rootCmd.Execute()
	if err != nil {
		os.Exit(1)
	}
}

func init() {
	rootCmd.PersistentFlags().String(userKey, "", "UUID of user for request")

	rootCmd.PersistentFlags().String(limitKey, "", "limit usages for link")
	rootCmd.PersistentFlags().String(deadlineKey, "", "deadline for link, format dd/mm/yy")
}
