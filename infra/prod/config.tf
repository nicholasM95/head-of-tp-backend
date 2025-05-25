terraform {
  required_providers {

    helm = {
      source  = "hashicorp/helm"
      version = "2.17.0"
    }

    aws = {
      source  = "hashicorp/aws"
      version = "5.88.0"
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