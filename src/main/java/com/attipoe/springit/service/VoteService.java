package com.attipoe.springit.service;

import com.attipoe.springit.domain.Vote;
import com.attipoe.springit.repository.LinkRepository;
import com.attipoe.springit.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final LinkRepository linkRepository;

    public VoteService(VoteRepository voteRepository, LinkRepository linkRepository) {
        this.voteRepository = voteRepository;
        this.linkRepository = linkRepository;
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
