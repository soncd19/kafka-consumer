package com.vnpay.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Set;

import static java.util.Collections.emptySet;

final class PartialSyncCommitPolicy<K, V> extends AbstractRecommitAwareCommitPolicy<K, V> {
    PartialSyncCommitPolicy(Consumer<K, V> consumer,
                            Duration syncCommitRetryInterval,
                            int maxAttemptsForEachSyncCommit,
                            Duration forceWholeCommitInterval) {
        super(consumer, syncCommitRetryInterval, maxAttemptsForEachSyncCommit, forceWholeCommitInterval);
    }

    @Override
    Set<TopicPartition> tryCommit0(boolean noPendingRecords, ProcessRecordsProgress progress) {
        if (progress.noOffsetsToCommit()) {
            return emptySet();
        }

        if (noPendingRecords) {
            final Set<TopicPartition> completePartitions = fullCommitSync(progress);
            updateNextRecommitTime();
            return completePartitions;
        }

        return partialCommitSync(progress);
    }
}
