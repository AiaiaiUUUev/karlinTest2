package com.karlin.user.common.exception

sealed class Failure {

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}