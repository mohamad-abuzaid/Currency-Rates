package com.code.challenge.domain.common

import androidx.annotation.Keep
import com.code.challenge.domain.common.ResourceType.BAD_REQUEST_ERROR
import com.code.challenge.domain.common.ResourceType.CONNECTION_ERROR
import com.code.challenge.domain.common.ResourceType.EMPTY_DATA
import com.code.challenge.domain.common.ResourceType.ERROR
import com.code.challenge.domain.common.ResourceType.ERROR_MESSAGE
import com.code.challenge.domain.common.ResourceType.FORBIDDEN_ERROR
import com.code.challenge.domain.common.ResourceType.LOADING
import com.code.challenge.domain.common.ResourceType.NOT_ACTIVATED_ERROR
import com.code.challenge.domain.common.ResourceType.NOT_ENOUGH_BALANCE
import com.code.challenge.domain.common.ResourceType.NOT_FOUND
import com.code.challenge.domain.common.ResourceType.SUCCESS
import com.code.challenge.domain.common.ResourceType.UNAUTHORIZED_ERROR

@Keep
class Resource<T> {
  val resourceType: ResourceType
  var data: T? = null
  var message: String? = null
    private set
  var statusCode: Int? = null
    private set
  var errors: Map<String, Array<String>>? = null
    private set

  constructor(
    resourceType: ResourceType,
    data: T?,
    message: String?
  ) {
    this.resourceType = resourceType
    this.data = data
    this.message = message
  }

  constructor(
    resourceType: ResourceType,
    data: T?
  ) {
    this.resourceType = resourceType
    this.data = data
  }

  constructor(
    resourceType: ResourceType,
    message: String?
  ) {
    this.resourceType = resourceType
    this.message = message
  }

  constructor(
    resourceType: ResourceType,
    data: T?,
    errors: Map<String, Array<String>>
  ) {
    this.resourceType = resourceType
    this.data = data
    this.errors = errors
  }

  constructor(
    resourceType: ResourceType,
    errors: Map<String, Array<String>>
  ) {
    this.resourceType = resourceType
    this.errors = errors
  }

  constructor(
    resourceType: ResourceType,
    statusCode: Int,
    message: String? = null
  ) {
    this.resourceType = resourceType
    this.statusCode = statusCode
    this.message = message
  }

  companion object {
    fun <T> loading(): Resource<T> = Resource(LOADING, data = null)

    fun <T> loading(data: T): Resource<T> = Resource(LOADING, data)

    fun <T> error(data: T?): Resource<T> = Resource(ERROR, data)

    fun <T> success(): Resource<T> = Resource(SUCCESS, data = null)

    fun <T> success(data: T): Resource<T> = Resource(SUCCESS, data)

    fun <T> success(
      data: T,
      message: String?
    ): Resource<T> =
      Resource(SUCCESS, data, message)

    fun <T> successMessage(message: String?): Resource<T> =
      Resource(SUCCESS, message)

    fun <T> connectionError(data: T?): Resource<T> =
      Resource(CONNECTION_ERROR, data = data)

    fun <T> emptyDataError(): Resource<T> = Resource(EMPTY_DATA, data = null)

    fun <T> unauthorizedError(message: String): Resource<T> =
      Resource(UNAUTHORIZED_ERROR, message = message)

    fun <T> badRequestError(message: String): Resource<T> =
      Resource(BAD_REQUEST_ERROR, message = message)

    fun <T> forbiddenError(message: String): Resource<T> =
      Resource(FORBIDDEN_ERROR, message = message)

    fun <T> notFoundError(message: String?): Resource<T> =
      Resource(NOT_FOUND, message = message)

    fun <T> notActivatedError(message: String): Resource<T> =
      Resource(NOT_ACTIVATED_ERROR, message = message)

    fun <T> error(errors: Map<String, Array<String>>): Resource<T> =
      Resource(ERROR, errors)

    fun <T> errorWithData(data: T?, errors: Map<String, Array<String>>): Resource<T> =
      Resource(ERROR, data, errors)

    fun <T> notEnoughBalanceError(message: String): Resource<T> =
      Resource(NOT_ENOUGH_BALANCE, message)

    fun <T> errorMessage(message: String?): Resource<T> = Resource(ERROR_MESSAGE, message)

    fun <T> validationError(
      statusCode: Int,
      message: String? = null
    ): Resource<T> =
      Resource(ResourceType.VALIDATION_ERROR, statusCode, message)

    fun <T> unknownError(
      message: String? = null
    ): Resource<T> =
      Resource(ResourceType.UNKNOWN_ERROR, message)
  }
}