variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-2"
}

variable "project_name" {
  description = "Project name"
  type        = string
  default     = "ms-franchise-neg"
}

variable "aws_account_id" {
  description = "AWS account ID"
  type        = string
}

variable "db_url" {
  description = "R2DBC DB URL"
  type        = string
}

variable "db_username" {
  type = string
}

variable "db_password" {
  type      = string
  sensitive = true
}

variable "image_tag" {
  description = "docker image tag"
  type        = string
}
