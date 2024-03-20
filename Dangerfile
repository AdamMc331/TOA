message "Thanks @#{github.pr_author}!"

if github.pr_body.length == 0
    fail "Please provide a summary in the Pull Request description."
end

if git.lines_of_code > 500
    warn "Please consider breaking up this pull request."
end

if github.pr_labels.empty?
    warn "Please add labels to this PR."
end

if git.deletions > git.insertions
    message  "🎉 Code Cleanup!"
end

if !git.modified_files.include?("StreamHistory.md") && !github.branch_for_head.include?("renovate")
    fail "Please update StreamHistory.md with relevant information."
end
