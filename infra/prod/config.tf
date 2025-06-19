terraform {
  required_providers {

    helm = {
      source  = "hashicorp/helm"
      version = "3.0.1"
    }

    aws = {
      source  = "hashicorp/aws"
      version = "5.98.0"
    }
  }

  backend "s3" {
    bucket = "head-of-tp-backend-prd-terraform-state"
    key    = "terraform.tfstate"
    region = "eu-west-1"
  }
}

provider "helm" {
  kubernetes {
    config_path = "~/.kube/config"
  }
}