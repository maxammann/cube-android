#!/bin/bash

protoc -I=$1 --java_out=$2 $3
