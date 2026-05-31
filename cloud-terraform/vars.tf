// Common

variable "service_account_key_file" {
  type    = string
  default = "./authorized_key.json"
}

variable "cloud_id" {
  type = string
  default = "b1gltbjsqrolbtbdscl1"
}

variable "folder_id" {
  type = string
  default = "b1gp84q52lom6l4pl6mj"
}

variable "zone" {
  type    = string
  default = "ru-central1-d"
}


variable "network_name" {
  type = string
}

variable "v4_cidr_blocks" {
  type = list(string)
}


variable "vm_name" {
  type = string
}

variable "image_id" {
  type = string
}

variable "ip_address" {
  type = string
}