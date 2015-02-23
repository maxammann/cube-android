#!/bin/bash
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

protoc -I=$DIR --java_out=$DIRsrc/main/java $DIR/../lm.proto
