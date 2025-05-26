module "vault" {
  source     = "git::https://github.com/nicholasM95/terraform-modules.git//modules/vault?ref=v1.8.20"
  vault_path = var.name
}

module "vault_connection" {
  depends_on                = [module.vault]
  source                    = "git::https://github.com/nicholasM95/terraform-modules.git//modules/vault-k8s?ref=v1.8.20"
  vault_path                = var.name
  kubernetes_ca_cert        = var.kubernetes_ca_cert
  kubernetes_internal_host  = var.kubernetes_internal_host
  service_account_name      = var.name
  service_account_namespace = var.namespace
}