variable "docker_config" {
  description = "Docker config to pull an image"
  type        = string
}

variable "namespace" {
  description = "Kubernetes namespace"
  type        = string
  default     = "head-of-tp"
}

variable "name" {
  description = "Application name"
  type        = string
  default     = "head-of-tp"
}

variable "image_tag" {
  description = "Image tag"
  type        = string
}

variable "vault_uri" {
  description = "Vault uri"
  type        = string
}

variable "kubernetes_ca_cert" {
  description = "Kubernetes ca cert"
  type        = string
}

variable "kubernetes_internal_host" {
  description = "Kubernetes internal host"
  type        = string
}
